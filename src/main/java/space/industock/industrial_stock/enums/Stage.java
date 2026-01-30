package space.industock.industrial_stock.enums;

public enum Stage {
  PENDENTE_PRODUCAO,
  EM_PRODUCAO,
  PENDENTE_ENTREGA,
  ENTREGUE;


  public Stage next() {
    Stage[] values = values();
    int index = this.ordinal() + 1;

    if (index < values.length) {
      return values[index];
    }

    return null;
  }


}

