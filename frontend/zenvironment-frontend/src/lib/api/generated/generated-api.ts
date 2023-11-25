export interface NewPlantDto {
  /** @format uuid */
  plantTypeId?: string;
  /** @format date */
  plantedAt?: string;
}

export interface PlantDto {
  /** @format uuid */
  id?: string;
  plantType?: PlantTypeDto;
  /** @format uuid */
  gardenId?: string;
  /** @format double */
  allProducedOxygenInKilograms?: number;
  /** @format double */
  allFixatedCO2InKilograms?: number;
  /** @format double */
  allWaterConsumptionInLiters?: number;
  /** @format int32 */
  daysTillHarvest?: number;
  /** @format date */
  plantedAt?: string;
  /** @format date */
  uprootedAt?: string;
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

export interface HarvestDto {
  /** @format uuid */
  id?: string;
  /** @format double */
  amount?: number;
  harvestUnit?: string;
  /** @format date */
  harvestDate?: string;
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
}

export interface MinimalCommunity {
  /** @format uuid */
  id?: string;
  name?: string;
}

export interface CommunityDto {
  /** @format uuid */
  id?: string;
  name?: string;
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
     * No description
     *
     * @tags zen-controller
     * @name AddPlant
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
     * No description
     *
     * @tags zen-controller
     * @name HarvestPlant
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
     * No description
     *
     * @tags zen-controller
     * @name GetMyGarden
     * @request GET:/my-garden
     */
    getMyGarden: (params: RequestParams = {}) =>
      this.request<GardenDto, any>({
        path: `/my-garden`,
        method: "GET",
        ...params,
      }),

    /**
     * No description
     *
     * @tags zen-controller
     * @name GetPlantById
     * @request GET:/my-garden/plants/{plantId}
     */
    getPlantById: (plantId: string, params: RequestParams = {}) =>
      this.request<PlantDto, any>({
        path: `/my-garden/plants/${plantId}`,
        method: "GET",
        ...params,
      }),
  };
  plantTypes = {
    /**
     * No description
     *
     * @tags zen-controller
     * @name GetPlantTypes
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
     * No description
     *
     * @tags zen-controller
     * @name GetMyCommunity
     * @request GET:/my-community
     */
    getMyCommunity: (params: RequestParams = {}) =>
      this.request<CommunityDto, any>({
        path: `/my-community`,
        method: "GET",
        ...params,
      }),

    /**
     * No description
     *
     * @tags zen-controller
     * @name GetMyCommunityChallenges
     * @request GET:/my-community/challenges
     */
    getMyCommunityChallenges: (params: RequestParams = {}) =>
      this.request<ChallengeDto[], any>({
        path: `/my-community/challenges`,
        method: "GET",
        ...params,
      }),
  };
}
