public class Product {
    private String product;
    private int amount;
    private boolean isExpense;

    public Product(String[] lineContents) {
        //для считывания годового отчёта
        if (lineContents.length == 3){
            this.product = lineContents[0];
            this.amount = Integer.parseInt(lineContents[1]);
            this.isExpense = Boolean.parseBoolean(lineContents[2]);
        }
        //для считывания месячного отчета
        else if(lineContents.length == 4){
            this.product = lineContents[0];
            this.amount = Integer.parseInt(lineContents[2]) * Integer.parseInt(lineContents[3]);
            this.isExpense = Boolean.parseBoolean(lineContents[1]);
        }
    }

    public String getProduct() {
        return product;
    }

    public boolean isExpense() {
        return isExpense;
    }

    public int getAmount() {
        return amount;
    }
}
