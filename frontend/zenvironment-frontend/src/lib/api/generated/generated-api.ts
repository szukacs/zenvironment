export interface ExchangeDto {
  exchangeId?: string;
  vendorId?: string;
  receiverId?: string;
  description?: string;
  profileImageUrl?: string;
  productImageUrl?: string;
  gardenName?: string;
  accepted?: boolean;
}

export interface NewPlantDto {
  /** @format int32 */
  x?: number;
  /** @format int32 */
  y?: number;
  /** @format uuid */
  plantTypeId?: string;
  /** @format date-time */
  plantedAt?: string;
}

export interface HarvestDto {
  /** @format uuid */
  id?: string;
  /** @format uuid */
  plantId?: string;
  /** @format double */
  amount?: number;
  harvestUnit?: string;
  /** @format date */
  harvestDate?: string;
}

export interface PlantDto {
  /** @format uuid */
  id?: string;
  /** @format uuid */
  gardenId?: string;
  /** @format int32 */
  x?: number;
  /** @format int32 */
  y?: number;
  plantType?: PlantTypeDto;
  /** @format double */
  allProducedOxygenInKilograms?: number;
  /** @format double */
  allFixatedCO2InKilograms?: number;
  /** @format double */
  allWaterConsumptionInLiters?: number;
  /** @format double */
  allHarvestedAmount?: number;
  /** @format int32 */
  daysTillHarvest?: number;
  /** @format date */
  plantedAt?: string;
  harvests?: HarvestDto[];
}

export interface PlantTypeDto {
  /** @format uuid */
  id?: string;
  name?: string;
  imageUrl?: string;
  /** @format double */
  averageOxygenProductionInKilogramsPerDay?: number;
  /** @format double */
  averageCO2FixationInKilogramsPerDay?: number;
  harvestUnit?: string;
}

export interface NewHarvestDto {
  /** @format double */
  amount?: number;
  /** @format date */
  harvestDate?: string;
}

export interface NewGardenDto {
  name?: string;
}

export interface GardenDto {
  /** @format uuid */
  id?: string;
  name?: string;
  /** @format double */
  allProducedOxygenInKilograms?: number;
  /** @format double */
  allFixatedCO2InKilograms?: number;
  /** @format double */
  allWaterConsumptionInLiters?: number;
  community?: MinimalCommunity;
  plants?: PlantDto[];
  plantSummaries?: PlantSummary[];
}

export interface MinimalCommunity {
  /** @format uuid */
  id?: string;
  name?: string;
}

export interface PlantSummary {
  plantType?: PlantTypeDto;
  /** @format int32 */
  plantCount?: number;
  /** @format double */
  allProducedOxygenInKilograms?: number;
  /** @format double */
  allFixatedCO2InKilograms?: number;
}

export interface Message {
  message?: string;
  conversationId?: string;
}

export interface HarvestSummary {
  plantName?: string;
  /** @format double */
  amount?: number;
  unit?: string;
  /** @format int64 */
  harvestCount?: number;
}

export interface CommunityDto {
  /** @format uuid */
  id?: string;
  name?: string;
  /** @format double */
  allProducedOxygenInKilograms?: number;
  /** @format double */
  allFixatedCO2InKilograms?: number;
  gardens?: GardenDto[];
}

export interface ChallengeDto {
  /** @format uuid */
  id?: string;
  community?: MinimalCommunity;
  /** @format int32 */
  level?: number;
  challengeName?: string;
  challengeDescription?: string;
  imageUrl?: string;
  color?: string;
  /** @format double */
  previousLevelTarget?: number;
  /** @format double */
  nextLevelTarget?: number;
  /** @format double */
  currentProgress?: number;
}

import type { AxiosInstance, AxiosRequestConfig, AxiosResponse, HeadersDefaults, ResponseType } from "axios";
import axios from "axios";

export type QueryParamsType = Record<string | number, any>;

export interface FullRequestParams extends Omit<AxiosRequestConfig, "data" | "params" | "url" | "responseType"> {
  /** set parameter to `true` for call `securityWorker` for this request */
  secure?: boolean;
  /** request path */
  path: string;
  /** content type of request body */
  type?: ContentType;
  /** query params */
  query?: QueryParamsType;
  /** format of response (i.e. response.json() -> format: "json") */
  format?: ResponseType;
  /** request body */
  body?: unknown;
}

export type RequestParams = Omit<FullRequestParams, "body" | "method" | "query" | "path">;

export interface ApiConfig<SecurityDataType = unknown> extends Omit<AxiosRequestConfig, "data" | "cancelToken"> {
  securityWorker?: (
    securityData: SecurityDataType | null,
  ) => Promise<AxiosRequestConfig | void> | AxiosRequestConfig | void;
  secure?: boolean;
  format?: ResponseType;
}

export enum ContentType {
  Json = "application/json",
  FormData = "multipart/form-data",
  UrlEncoded = "application/x-www-form-urlencoded",
  Text = "text/plain",
}

export class HttpClient<SecurityDataType = unknown> {
  public instance: AxiosInstance;
  private securityData: SecurityDataType | null = null;
  private securityWorker?: ApiConfig<SecurityDataType>["securityWorker"];
  private secure?: boolean;
  private format?: ResponseType;

  constructor({ securityWorker, secure, format, ...axiosConfig }: ApiConfig<SecurityDataType> = {}) {
    this.instance = axios.create({ ...axiosConfig, baseURL: axiosConfig.baseURL || "http://localhost:8080" });
    this.secure = secure;
    this.format = format;
    this.securityWorker = securityWorker;
  }

  public setSecurityData = (data: SecurityDataType | null) => {
    this.securityData = data;
  };

  protected mergeRequestParams(params1: AxiosRequestConfig, params2?: AxiosRequestConfig): AxiosRequestConfig {
    const method = params1.method || (params2 && params2.method);

    return {
      ...this.instance.defaults,
      ...params1,
      ...(params2 || {}),
      headers: {
        ...((method && this.instance.defaults.headers[method.toLowerCase() as keyof HeadersDefaults]) || {}),
        ...(params1.headers || {}),
        ...((params2 && params2.headers) || {}),
      },
    };
  }

  protected stringifyFormItem(formItem: unknown) {
    if (typeof formItem === "object" && formItem !== null) {
      return JSON.stringify(formItem);
    } else {
      return `${formItem}`;
    }
  }

  protected createFormData(input: Record<string, unknown>): FormData {
    return Object.keys(input || {}).reduce((formData, key) => {
      const property = input[key];
      const propertyContent: any[] = property instanceof Array ? property : [property];

      for (const formItem of propertyContent) {
        const isFileType = formItem instanceof Blob || formItem instanceof File;
        formData.append(key, isFileType ? formItem : this.stringifyFormItem(formItem));
      }

      return formData;
    }, new FormData());
  }

  public request = async <T = any, _E = any>({
    secure,
    path,
    type,
    query,
    format,
    body,
    ...params
  }: FullRequestParams): Promise<AxiosResponse<T>> => {
    const secureParams =
      ((typeof secure === "boolean" ? secure : this.secure) &&
        this.securityWorker &&
        (await this.securityWorker(this.securityData))) ||
      {};
    const requestParams = this.mergeRequestParams(params, secureParams);
    const responseFormat = format || this.format || undefined;

    if (type === ContentType.FormData && body && body !== null && typeof body === "object") {
      body = this.createFormData(body as Record<string, unknown>);
    }

    if (type === ContentType.Text && body && body !== null && typeof body !== "string") {
      body = JSON.stringify(body);
    }

    return this.instance.request({
      ...requestParams,
      headers: {
        ...(requestParams.headers || {}),
        ...(type && type !== ContentType.FormData ? { "Content-Type": type } : {}),
      },
      params: query,
      responseType: responseFormat,
      data: body,
      url: path,
    });
  };
}

/**
 * @title OpenAPI definition
 * @version v0
 * @baseUrl http://localhost:8080
 */
export class Api<SecurityDataType extends unknown> extends HttpClient<SecurityDataType> {
  myGarden = {
    /**
     * @description Return a success message tha request was accepted by user
     *
     * @tags zen-controller
     * @name AcceptExchange
     * @summary User can accept another user's exchange request
     * @request PUT:/my-garden/exchanges
     */
    acceptExchange: (
      query: {
        exchangeDto: ExchangeDto;
      },
      params: RequestParams = {},
    ) =>
      this.request<object, any>({
        path: `/my-garden/exchanges`,
        method: "PUT",
        query: query,
        ...params,
      }),

    /**
     * @description Returns back all exchange requests to which the user is affiliated to
     *
     * @tags zen-controller
     * @name AddExchange
     * @summary User creates a new exchange request to give away plants
     * @request POST:/my-garden/exchanges
     */
    addExchange: (
      query: {
        exchangeDto: ExchangeDto;
      },
      params: RequestParams = {},
    ) =>
      this.request<ExchangeDto[], any>({
        path: `/my-garden/exchanges`,
        method: "POST",
        query: query,
        ...params,
      }),

    /**
     * @description Returns newly created plant
     *
     * @tags zen-controller
     * @name AddPlant
     * @summary Creates new plant in given garden
     * @request POST:/my-garden/plants
     */
    addPlant: (data: NewPlantDto, params: RequestParams = {}) =>
      this.request<PlantDto, any>({
        path: `/my-garden/plants`,
        method: "POST",
        body: data,
        type: ContentType.Json,
        ...params,
      }),

    /**
     * @description Returns harvesting details
     *
     * @tags zen-controller
     * @name HarvestPlant
     * @summary Harvesting plant based on user input
     * @request POST:/my-garden/plants/{plantId}/harvest
     */
    harvestPlant: (plantId: string, data: NewHarvestDto, params: RequestParams = {}) =>
      this.request<HarvestDto, any>({
        path: `/my-garden/plants/${plantId}/harvest`,
        method: "POST",
        body: data,
        type: ContentType.Json,
        ...params,
      }),

    /**
     * @description Returns user's garden
     *
     * @tags zen-controller
     * @name GetMyGarden
     * @summary Find user's garden
     * @request GET:/my-garden
     */
    getMyGarden: (params: RequestParams = {}) =>
      this.request<GardenDto, any>({
        path: `/my-garden`,
        method: "GET",
        ...params,
      }),

    /**
     * @description Returns plant based on plantId
     *
     * @tags zen-controller
     * @name GetPlantById
     * @summary Finding a plant
     * @request GET:/my-garden/plants/{plantId}
     */
    getPlantById: (plantId: string, params: RequestParams = {}) =>
      this.request<PlantDto, any>({
        path: `/my-garden/plants/${plantId}`,
        method: "GET",
        ...params,
      }),

    /**
     * @description Return harvest details
     *
     * @tags zen-controller
     * @name GetMyHarvests
     * @summary Initiating harvest for user's garden
     * @request GET:/my-garden/harvests
     */
    getMyHarvests: (params: RequestParams = {}) =>
      this.request<HarvestSummary[], any>({
        path: `/my-garden/harvests`,
        method: "GET",
        ...params,
      }),

    /**
     * @description Returns all exchanges to which user is affiliated to
     *
     * @tags zen-controller
     * @name FindAllExchangesBelongingToGarden
     * @summary Finding all exchanges belonging to given garden
     * @request GET:/my-garden/exchanges/{gardenId}
     */
    findAllExchangesBelongingToGarden: (gardenId: string, params: RequestParams = {}) =>
      this.request<ExchangeDto[], any>({
        path: `/my-garden/exchanges/${gardenId}`,
        method: "GET",
        ...params,
      }),
  };
  gardens = {
    /**
     * @description Returns newly created garden
     *
     * @tags zen-controller
     * @name CreateGarden
     * @summary Creates a garden with required parameters
     * @request POST:/gardens
     */
    createGarden: (data: NewGardenDto, params: RequestParams = {}) =>
      this.request<GardenDto, any>({
        path: `/gardens`,
        method: "POST",
        body: data,
        type: ContentType.Json,
        ...params,
      }),

    /**
     * @description Returns newly created plant
     *
     * @tags zen-controller
     * @name AddPlant1
     * @summary Creates new plant in given garden
     * @request POST:/gardens/{gardenId}/plants
     */
    addPlant1: (gardenId: string, data: NewPlantDto, params: RequestParams = {}) =>
      this.request<PlantDto, any>({
        path: `/gardens/${gardenId}/plants`,
        method: "POST",
        body: data,
        type: ContentType.Json,
        ...params,
      }),

    /**
     * @description Returns garden belonging to id
     *
     * @tags zen-controller
     * @name GetGardenById
     * @summary Gets garden by unique gardenId
     * @request GET:/gardens/{gardenId}
     */
    getGardenById: (gardenId: string, params: RequestParams = {}) =>
      this.request<GardenDto, any>({
        path: `/gardens/${gardenId}`,
        method: "GET",
        ...params,
      }),
  };
  assistant = {
    /**
     * @description Old Sam answers all of your questions
     *
     * @tags assistant-controller
     * @name AskForAssistance
     * @summary Old Sam answers all of your questions
     * @request POST:/assistant/chat
     */
    askForAssistance: (data: Message, params: RequestParams = {}) =>
      this.request<Message, any>({
        path: `/assistant/chat`,
        method: "POST",
        body: data,
        type: ContentType.Json,
        ...params,
      }),
  };
  plantTypes = {
    /**
     * @description Returns all plant types with associated information
     *
     * @tags zen-controller
     * @name GetPlantTypes
     * @summary Get all available plant types in the app
     * @request GET:/plant-types
     */
    getPlantTypes: (params: RequestParams = {}) =>
      this.request<PlantTypeDto[], any>({
        path: `/plant-types`,
        method: "GET",
        ...params,
      }),
  };
  myCommunity = {
    /**
     * @description Returns community to which user belongs to
     *
     * @tags zen-controller
     * @name GetMyCommunity
     * @summary Initiating finding the community belonging to the user
     * @request GET:/my-community
     */
    getMyCommunity: (params: RequestParams = {}) =>
      this.request<CommunityDto, any>({
        path: `/my-community`,
        method: "GET",
        ...params,
      }),

    /**
     * @description Returns all exchanges within the community
     *
     * @tags zen-controller
     * @name FindAllExchangesBelongingToCommuniy
     * @summary Finding all exchanges belonging to given community
     * @request GET:/my-community/exchanges/{communityId}
     */
    findAllExchangesBelongingToCommuniy: (communityId: string, params: RequestParams = {}) =>
      this.request<ExchangeDto[], any>({
        path: `/my-community/exchanges/${communityId}`,
        method: "GET",
        ...params,
      }),

    /**
     * @description Returns back actual state of the challenges of the user's commmunity
     *
     * @tags zen-controller
     * @name GetMyCommunityChallenges
     * @summary Finding challenges for the community
     * @request GET:/my-community/challenges
     */
    getMyCommunityChallenges: (params: RequestParams = {}) =>
      this.request<ChallengeDto[], any>({
        path: `/my-community/challenges`,
        method: "GET",
        ...params,
      }),
  };
  cleanup = {
    /**
     * @description Returns with a status code ok in case everything went right
     *
     * @tags cleanup-controller
     * @name DeleteGeneratedGardens
     * @summary A technical endpoint to delete all gardens unnecessarily generated
     * @request DELETE:/cleanup
     */
    deleteGeneratedGardens: (params: RequestParams = {}) =>
      this.request<void, any>({
        path: `/cleanup`,
        method: "DELETE",
        ...params,
      }),
  };
}
