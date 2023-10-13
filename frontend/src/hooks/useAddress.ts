import { useState } from "react";
import {
  getDepartmentsAPI,
  getMunicipalitiesAPI,
  getProvincesAPI,
} from "../data/address";

export function useAddress() {
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const getProvinces = async () => {
    try {
      setLoading(true);
      const response = await getProvincesAPI();
      setLoading(false);
      return response;
    } catch (error: any) {
      setLoading(false);
      setError(error);
    }
  };

  const getDepartments = async (id: string) => {
    try {
      setLoading(true);
      const response = await getDepartmentsAPI(id);
      setLoading(false);
      return response;
    } catch (error: any) {
      setLoading(false);
      setError(error);
    }
  };

  const getMunicipalities = async (id: string) => {
    try {
      setLoading(true);
      const resultado = await getMunicipalitiesAPI(id);
      setLoading(false);
      return resultado;
    } catch (error: any) {
      setLoading(false);
      setError(error);
    }
  };

  return {
    loading,
    error,
    getProvinces,
    getDepartments,
    getMunicipalities,
  };
}
