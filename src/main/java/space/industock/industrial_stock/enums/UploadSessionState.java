package space.industock.industrial_stock.enums;

public enum UploadSessionState {
  PENDING,
  UPLOADING,
  READY_TO_COMMIT,
  COMMITTED,
  ABORTED,
  EXPIRED
}
