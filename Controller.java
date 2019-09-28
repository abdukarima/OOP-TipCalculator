package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import org.w3c.dom.Text;

import java.text.NumberFormat;

public class Controller {
    private final String currency = NumberFormat.getCurrencyInstance().getCurrency().getSymbol();

    @FXML
    public TextField tfTip;

    private int tipPercentage = 0;
    private int currentTip = 0;
    private int currentPrice = 0;

    @FXML
    private TextField tfTotal;

    @FXML
    private TextField tfPrice;

    @FXML
    private Slider sliderPercent;

    @FXML
    private Label labelTotal;

    @FXML
    private Label labelPercentage;

    @FXML
    void onCalculate(ActionEvent event) {
        calculate();
    }

    @FXML
    void initialize() {
        tfTip.setText("0");
        tfTotal.setText("0");
        onlyNumber(tfPrice);

        sliderPercent.setMax(16);
        sliderPercent.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            if (oldValue.intValue() % 5 == 0) {
                tipPercentage = oldValue.intValue();
                labelPercentage.setText(tipPercentage + "%");
                calculateTip();
            }
        });
    }

    private void onlyNumber(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            }
            if (textField.equals(tfPrice)) {
                calculateTip();
            }
        });
    }

    private void calculateTip() {
        if (!tfPrice.getText().isBlank()) {
            currentPrice = Integer.valueOf(tfPrice.getText());
            currentTip = ((int) (currentPrice / 100.0 * tipPercentage));
            tfTip.setText(String.valueOf(currentTip));
        }
    }

    private void calculate() {
        int total = currentPrice + currentTip;
        tfTotal.setText(String.valueOf(total));
    }
}