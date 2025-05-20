package lk.ijse.gdse;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class EmojiPicker {
    private final String [] em = new String[]{
            "😃","😄","😆","🥹","🧐","🤪","😉","😌","🥰","😗","😙","🥳","😒","🥵"
    };

    public void showEmojiPicker(TextField txt){
        Stage emojistage = new Stage();
        GridPane pane = new GridPane();

        int row =0;
        int col = 0;

        for (String emoji : em){
            Button emojiBtn = new Button(emoji);
            emojiBtn.setOnAction(actionEvent -> {
                txt.appendText(emoji);
                emojistage.close();
            });
            pane.add(emojiBtn,col,row);
            col++;
            if (col>5){
                col = 0;
                row++;
            }
        }

        Scene scene = new Scene(pane, 200,200);
        emojistage.setScene(scene);
        emojistage.setTitle("select emoji");
        emojistage.show();

    }
}
