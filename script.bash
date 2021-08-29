cd src/
javac -cp "../lib/*" unsw/dungeon/*.java
java --module-path ../lib --add-modules=javafx.controls,javafx.fxml unsw.dungeon.DungeonApplication