package com.nhn.pebblewebback.infra.etherscan;

import com.nhn.pebblewebback.infra.etherscan.dto.NftTransferEventsApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "geoip", url = "https://api-sepolia.etherscan.io/api", fallbackFactory = EtherScanApiFeignFallback.class)
public interface EtherScanApiFeign {

  @GetMapping("/")
  NftTransferEventsApiResponse getNftTransferEvents(
      @RequestParam("module") String module,
      @RequestParam("action") String action,
      @RequestParam("contractaddress") String contractAddress,
      @RequestParam("address") String address,
      @RequestParam("page") int page,
      @RequestParam("offset") int offset,
      @RequestParam("startblock") int startBlock,
      @RequestParam("endblock") int endBlock,
      @RequestParam("sort") String sort,
      @RequestParam("apikey") String apikey);
}


