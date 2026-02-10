package space.industock.industrial_stock.dto.session;

import java.util.List;

public class CommitResponse {

  public Long clientId;
  public List<ServiceMapping> services;
  public List<PictureMapping> pictures;
}
