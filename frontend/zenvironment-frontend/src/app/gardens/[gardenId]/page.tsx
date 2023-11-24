interface GardenPageParams {
  params: {
    gardenId: string;
  };
}

export default function GardenPage({params}: GardenPageParams) {
  return (
    <>
      {params.gardenId}
    </>
  )
}