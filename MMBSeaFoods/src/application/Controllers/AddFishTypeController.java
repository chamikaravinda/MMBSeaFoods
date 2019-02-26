package application.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.controlsfx.control.Notifications;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RequiredFieldValidator;

import application.Models.Boat;
import application.Models.Fish_Lot;
import application.Models.Foreign_Fish_types;
import application.Models.ProfiteAndLose;
import application.Models.Third_Party_Account;
import application.Models.Vehicles;
import application.Services.BoatService;
import application.Services.Fish_LotServices;
import application.Services.Foreign_Fish_typesServices;
import application.Services.ProfiteAndLossService;
import application.Services.Third_Party_AccountServices;
import application.Services.VehiclesServices;
import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class AddFishTypeController implements Initializable {

	@FXML
	private AnchorPane FishType;

	@FXML
	private JFXButton btnAdd;

	@FXML
	private JFXTextField txtName;

	@FXML
	private JFXTextField txtU10;

	@FXML
	private JFXTextField txt10T15;

	@FXML
	private JFXTextField txt15T20;

	@FXML
	private JFXTextField txt20T25;

	@FXML
	private JFXTextField txt25T30;

	@FXML
	private JFXTextField txtA30;

	AnchorPane back;

	Foreign_Fish_typesServices service = new Foreign_Fish_typesServices();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		RequiredFieldValidator Name = new RequiredFieldValidator();
		NumberValidator numberValidator = new NumberValidator();

		txtName.getValidators().add(Name);
		Name.setMessage("Please input the Fish Type");

		txtName.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (!newValue) {
					txtName.validate();
				}

			}

		});

		txtU10.getValidators().add(numberValidator);
		numberValidator.setMessage("Please input correct values");

		txtU10.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (!newValue) {
					txtU10.validate();
				}

			}

		});

		txt10T15.getValidators().add(numberValidator);
		numberValidator.setMessage("Please input correct values");
		
		txt10T15.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (!newValue) {
					txt10T15.validate();
				}

			}

		});
		
		txt15T20.getValidators().add(numberValidator);
		numberValidator.setMessage("Please input correct values");
		
		txt15T20.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (!newValue) {
					txt15T20.validate();
				}

			}

		});
		
		txt20T25.getValidators().add(numberValidator);
		numberValidator.setMessage("Please input correct values");
		
		txt20T25.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (!newValue) {
					txt20T25.validate();
				}

			}

		});
		
		txt25T30.getValidators().add(numberValidator);
		numberValidator.setMessage("Please input correct values");
		
		txt25T30.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (!newValue) {
					txt25T30.validate();
				}

			}

		});
		
		txtA30.getValidators().add(numberValidator);
		numberValidator.setMessage("Please input correct values");
		
		txtA30.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (!newValue) {
					txtA30.validate();
				}

			}

		});


	}

	public void AddFishType(ActionEvent event) throws SQLException, IOException {
		Foreign_Fish_types type = new Foreign_Fish_types();
		type.setName(txtName.getText());
		type.setPrice_U10(Double.parseDouble(txtU10.getText()));
		type.setPrice_10T15(Double.parseDouble(txt10T15.getText()));
		type.setPrice_15T20(Double.parseDouble(txt15T20.getText()));
		type.setPrice_20T25(Double.parseDouble(txt20T25.getText()));
		type.setPrice_25T30(Double.parseDouble(txt25T30.getText()));
		type.setPrice_A30(Double.parseDouble(txtA30.getText()));

		if (service.addForeign_Fish_Type(type)) {
			Notifications notifications = Notifications.create();
			notifications.title("Succesfull");
			notifications.text("Fish Type added succesfully");
			notifications.graphic(null);
			notifications.hideAfter(Duration.seconds(2));
			notifications.position(Pos.CENTER);
			notifications.showConfirm();

			back = FXMLLoader.load(getClass().getResource("/application/Views/Ftrade/FishTypes.fxml"));
			setNode(back);

		} else {
			Notifications notifications = Notifications.create();
			notifications.title("Error");
			notifications.text("Fish Type unsuccesfull");
			notifications.graphic(null);
			notifications.hideAfter(Duration.seconds(2));
			notifications.position(Pos.CENTER);
			notifications.showError();
		}

	}

	void setNode(Node node) {

		FishType.getChildren().clear();
		FishType.setTopAnchor(node, 0.0);
		FishType.setRightAnchor(node, 0.0);
		FishType.setLeftAnchor(node, 0.0);
		FishType.setBottomAnchor(node, 0.0);
		FishType.getChildren().addAll((Node) node);

		FadeTransition ft = new FadeTransition(Duration.millis(1500));
		ft.setNode(node);
		ft.setFromValue(0.1);
		ft.setToValue(1);
		ft.setCycleCount(1);
		ft.setAutoReverse(false);
		ft.play();

	}

	public void back(ActionEvent event) throws IOException {
		back = FXMLLoader.load(getClass().getResource("/application/Views/Ftrade/FishTypes.fxml"));
		setNode(back);

	}

}
