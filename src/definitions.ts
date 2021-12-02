export interface AdIdPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
