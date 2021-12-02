import { WebPlugin } from '@capacitor/core';

import type { AdIdPlugin } from './definitions';

export class AdIdWeb extends WebPlugin implements AdIdPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
