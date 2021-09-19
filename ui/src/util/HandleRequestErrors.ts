import { StorageService } from "./StorageService";

export const handleErrors = (
  error: any,
  setLoading: (loading: boolean) => void
) => {
  if (error.status === 404) {
    StorageService.getInstance().removeItem("session");
  } else {
    alert(error.error);
  }
  setLoading(false);
};

export const isError = (result: any) => {
  return !result || result.error;
};

export const defaultErrorHandler = (
  error: any,
  setLoading: (loading: boolean) => void
) => {
  if (isError(error)) {
    handleErrors(error, setLoading);
    return;
  }
  alert("Uknow error");
  setLoading(false);
  return;
};
