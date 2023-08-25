// import { BASE_API } from "../utils/constants";

export async function getProvincesAPI() {
  try {
    const url = `https://apis.datos.gob.ar/georef/api/provincias`;
    const params = {
      method: "GET",
    };
    const response = await fetch(url, params);
    if (response.status !== 200) {
      throw new Error("Error al busacar las provincias");
    }
    const result = await response.json();
    return result;
  } catch (error) {
    throw error;
  }
}

export async function getDepartmentsAPI(id:string) {
  try {
    const url = `https://apis.datos.gob.ar/georef/api/departamentos?provincia=${id}&max=5000`;
    const params = {
      method: "GET",
    };
    const response = await fetch(url, params);
    if (response.status !== 200) {
      throw new Error("Error al busacar los departamentos");
    }
    const result = await response.json();
    return result;
  } catch (error) {
    throw error;
  }
}

export async function getMunicipalitiesAPI(id:string) {
  try {
    const url = `https://apis.datos.gob.ar/georef/api/municipios?provincia=${id}&max=5000`;
    const params = {
      method: "GET",
    };
    const response = await fetch(url, params);
    if (response.status !== 200) {
      throw new Error("Error al busacar los departamentos");
    }
    const result = await response.json();
    return result;
  } catch (error) {
    throw error;
  }
}

