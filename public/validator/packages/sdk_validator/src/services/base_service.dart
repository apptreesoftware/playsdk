part of sdk_validator.services;

class BaseService {
  http.Client client;
  Uri coreUrl;

  BaseService(this.client, this.coreUrl);
}
