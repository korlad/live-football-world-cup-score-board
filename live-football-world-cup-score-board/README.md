#  Live counting board

Live score board library for Football World Cup matches that shows all the ongoing games and
their scores.

## Java version

This project build with maven and java 11


## Usage

```java
import org.example.service.FootballGame

# returns 'id' of the newly created match
footballGame.newMatch('Mexico', 'Canada')

# update score of the provided match id
footballGame.updatePoint(1,0,2)

# finish the match and remove from live board
footballGame.finishMatch(1)

# provide live board information
footballGame.summary()
```

## Contributing

Pull requests are welcome. For major changes, please open an issue first
to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License

[MIT](https://choosealicense.com/licenses/mit/)