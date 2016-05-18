part of sdkwebvalidator.services;

class BaseService {
  http.Client client;
  Uri coreUrl;

  BaseService(this.client, this.coreUrl);
}
