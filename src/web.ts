import { WebPlugin } from '@capacitor/core';

import type { AdIdPlugin } from './definitions';

export class AdIdWeb extends WebPlugin implements AdIdPlugin {
  async getAdId(): Promise<{ id: string }> {
    return { id: 'none' };
  }
}
