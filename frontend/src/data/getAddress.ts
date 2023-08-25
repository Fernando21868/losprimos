export async function getProvinces() {
  const response = await fetch(
    "https://apis.datos.gob.ar/georef/api/provincias"
  );
  const body = (await response.json()) as unknown;
  // assertIsSingleCategory(body);
  return body;
}
