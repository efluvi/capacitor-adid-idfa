import { registerPlugin } from '@capacitor/core';

import type { AdIdPlugin } from './definitions';

const AdId = registerPlugin<AdIdPlugin>('AdId', {
  web: () => import('./web').then(m => new m.AdIdWeb()),
});

export * from './definitions';
export { AdId };
