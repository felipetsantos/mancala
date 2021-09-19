export class StorageService<TData> {
  private static instance: any;

  public static getInstance<TData>(): StorageService<TData> {
    if (!this.instance) {
      this.instance = new StorageService<TData>();
    }
    return this.instance;
  }

  public saveData(key: string, data: TData) {
    localStorage.setItem(key, JSON.stringify(data));
  }

  public contains(key: string) {
    const data = localStorage.getItem(key);
    if (!data) {
      return false;
    }
    return true;
  }

  public getData(key: string): TData {
    const userData = localStorage.getItem(key);
    if (!userData) {
      throw new Error("No data stored");
    }
    const transformedData = JSON.parse(userData);
    const data: TData = {
      ...transformedData,
    };
    return data;
  }

  public removeItem(key: string) {
    localStorage.removeItem(key);
  }
}
