package moe.quill.espr.core.engine

class GameManager {
    private var state = GameState.WAITING

    fun changeState(state: GameState){
        when(state){
            GameState.WAITING -> TODO()
            GameState.STARTING -> TODO()
            GameState.ACTIVE -> TODO()
            GameState.ENDING -> TODO()
        }
    }
}