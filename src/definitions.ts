export interface AdIdPlugin {
  getAdId(): Promise<{ id: string }>;
}
