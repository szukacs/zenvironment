interface PlantPageParams {
  params: {
    plantId: string;
  };
}

export default function PlantPage({params}: PlantPageParams) {
  return (
    <>
      {params.plantId}
    </>
  )
}