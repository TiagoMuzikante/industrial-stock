package space.industock.industrial_stock.event;

import space.industock.industrial_stock.domain.ServiceOrder;
import space.industock.industrial_stock.enums.PictureType;

import java.util.UUID;

public record CommitServicePicturesEvent(UUID sessionId, PictureType pictureType, ServiceOrder serviceOrder) {
}
