package com.example.snakeladderapril;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class SnakeLadder extends Application {
    public static final int tileSize=40,width=10,height=10;
    public static final int buttonLine=height*tileSize + 50 ,infoLine=buttonLine-30;
    private static Dice dice=new Dice();
    private Player playerOne,playerTwo;
    private boolean gameStarted=false,playerOneTurn=false,playerTwoTurn=false;
    private Pane createContent()
    {
        Pane root=new Pane();
        root.setPrefSize(width*tileSize,height*tileSize + 100);

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Tile tile=new Tile(tileSize);
                tile.setTranslateX(j*tileSize);
                tile.setTranslateY(i*tileSize);
                root.getChildren().add(tile);
            }
        }

        Image img=new Image("C:\\Users\\user\\IdeaProjects\\SnakeLadderApril\\src\\main\\img.png");
        ImageView board=new ImageView();
        board.setImage(img);
        board.setFitHeight(height*tileSize);
        board.setFitWidth(width*tileSize);

        //Buttons
        Button playerOneButton=new Button("Player One");
        Button playerTwoButton=new Button("Player Two");
        Button startButton=new Button("Start");

        playerOneButton.setTranslateX(35);
        playerOneButton.setTranslateY(buttonLine);
        playerTwoButton.setTranslateX(300);
        playerTwoButton.setTranslateY(buttonLine);
        startButton.setTranslateX(160);
        startButton.setTranslateY(buttonLine);

        //Info Disply
        Label playerOneLable=new Label("Your Turn! P1");
        Label playerTwoLable=new Label("Your Turn! P2");
        Label diceLable=new Label("Start The Game");

        playerOneLable.setTranslateY(infoLine);
        playerOneLable.setTranslateX(35);
        playerOneButton.setDisable(true);
        playerTwoLable.setTranslateY(infoLine);
        playerTwoLable.setTranslateX(300);
        playerTwoButton.setDisable(true);
        diceLable.setTranslateY(infoLine);
        diceLable.setTranslateX(150);

        playerOne =new Player(tileSize, Color.BLACK,"Ananya");
        playerTwo =new Player(tileSize-5,Color.ANTIQUEWHITE,"Aditi");

        //Player Action
        playerOneButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (gameStarted)
                {
                    if (playerOneTurn)
                    {
                        //playing the game
                        int diceValue= dice.getRolledDiceValue();
                        diceLable.setText("Dice Value for Player 1:"+diceValue);
                        playerOne.movePlayer(diceValue);

                        //Checking for Winning Condition
                        if (playerOne.isWinner())
                        {
                            diceLable.setText("Winner is "+playerOne.getName());
                            //Disable both buttons
                            playerOneTurn=false;
                            playerOneButton.setDisable(true);
                            playerOneLable.setText("");

                            playerTwoTurn=false;
                            playerTwoButton.setDisable(true);
                            playerTwoLable.setText("");

                            startButton.setDisable(false);
                            startButton.setText("Restart");
                            gameStarted=false;
                        }
                        else
                        {
                            //Disabling player 1
                            playerOneTurn=false;
                            playerOneButton.setDisable(true);
                            playerOneLable.setText("");

                            //Enabling player 2
                            playerTwoTurn=true;
                            playerTwoButton.setDisable(false);
                            playerTwoLable.setText("Your Turn "+playerTwo.getName());
                        }
                    }
                }
            }
        });
        playerTwoButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (gameStarted)
                {
                    if (playerTwoTurn)
                    {
                        //playing the game
                        int diceValue= dice.getRolledDiceValue();
                        diceLable.setText("Dice Value for Player 2:"+diceValue);
                        playerTwo.movePlayer(diceValue);

                        //Checking for Winning Condition
                        if (playerTwo.isWinner()) {
                            diceLable.setText("Winner is " + playerTwo.getName());
                            //Disable both buttons
                            playerTwoTurn=false;
                            playerTwoButton.setDisable(true);
                            playerTwoLable.setText("");

                            playerOneTurn=false;
                            playerOneButton.setDisable(true);
                            playerOneLable.setText("");

                            startButton.setDisable(false);
                            startButton.setText("Restart");
                            gameStarted=false;
                        }

                        else
                        {
                            //Disabling player 2
                            playerTwoTurn=false;
                            playerTwoButton.setDisable(true);
                            playerTwoLable.setText("");

                            //Enabling player 1
                            playerOneTurn=true;
                            playerOneButton.setDisable(false);
                            playerOneLable.setText("Your Turn "+playerOne.getName());
                        }
                    }
                }
            }
        });

        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                gameStarted=true;
                diceLable.setText("Game Started");
                startButton.setDisable(true);
                playerOneTurn=true;
                playerOneLable.setText("Your Turn "+playerOne.getName());
                playerOneButton.setDisable(false);
                playerOne.startingPosition();

                playerTwoTurn=false;
                playerTwoLable.setText("");
                playerTwoButton.setDisable(true);
                playerTwo.startingPosition();
            }
        });

        root.getChildren().addAll(board
                ,playerOneButton,startButton,playerTwoButton
                ,playerOneLable,playerTwoLable,diceLable,
                playerOne.getCoin(),playerTwo.getCoin());

        return root;
    }
    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(createContent());
        stage.setTitle("Snake & Ladder Game!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}