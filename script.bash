#cd src/
#javac -cp "../lib/*" unsw/dungeon/*.java
#java --module-path "../lib" --add-modules=javafx.controls,javafx.fxml unsw.dungeon.DungeonApplication
# java --module-path ../lib --add-modules javafx.controls,javafx.fxml -Dfile.encoding=UTF-8 -classpath "src/:lib/json.jar" unsw.dungeon.DungeonApplication 
#$  /usr/bin/env /usr/lib/jvm/java-11-openjdk-amd64/bin/java --module-path ./lib --add-modules javafx.controls,javafx.fxml -Dfile.encoding=UTF-8 @/tmp/cp_7lo0njwd1ef16f7j6jdoehn51.argfile unsw.dungeon.DungeonApplication
javac -cp "lib/*" src/unsw/dungeon/*.java
java --module-path "lib" --add-modules javafx.controls,javafx.fxml -Dfile.encoding=UTF-8 -classpath "src:lib/json.jar" unsw.dungeon.DungeonApplication 