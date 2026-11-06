import DeviceInfo from 'react-native-device-info';

/**
 * User-Agent for outbound Hugging Face requests.
 */
export const hfUserAgent = (): string =>
  `ZERO-Assistant/${DeviceInfo.getVersion()}`;