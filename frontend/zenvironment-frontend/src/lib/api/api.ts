import * as process from 'process'
import { Api } from './generated/generated-api';

export const api = new Api({
  baseURL: process.env.NEXT_PUBLIC_BACKEND_API_URL
});