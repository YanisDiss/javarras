package com.yanisdiss.javarras;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public final class InputManager {
    public static void handleMoveInput(GameEntity entity, float delta) {
        // Direct velocity control
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT)) {
            entity.move(delta,"left");
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT)) {
            entity.move(delta,"right");
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.DPAD_UP)) {
            entity.move(delta,"up");
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN)) {
            entity.move(delta,"down");
        }
    }
    public static void handleKeyCommands(GameEntity entity) {
        if (entity != null) { // targeted commands
            if (GameUtils.isTargeted(entity, GameUtils.getWorldMouse())) {
                if (Gdx.input.isKeyJustPressed(Input.Keys.B)) {
                    entity.setSize(entity.getSize() + 5);
                }
                if (Gdx.input.isKeyJustPressed(Input.Keys.N)) {
                    if (entity.getSize() > 5) entity.setSize(entity.getSize() - 5);
                }
                if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
                    entity.changeHealth(-entity.getMaxHealth() / 5f);
                }
                if (Gdx.input.isKeyJustPressed(Input.Keys.H)) {
                    entity.changeHealth(entity.getMaxHealth() / 5f);
                }
                if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
                    if (entity == entity.getMaster()){
                        GameEntity player = GameGlobals.entities.get(GameEntity.getPlayerId());
                        player.setControllable(false);
                        int playerId = entity.getId();
                        entity.setControllable(true);
                        GameEntity.setPlayerId(playerId);
                    }
                }


            }
        } else // general commands
        {
            {
                if (Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_ADD)) {
                    GameConfig.CLIENT_FOV += 0.1f;
                }
                if (Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_SUBTRACT)) {
                    if (GameConfig.CLIENT_FOV > 0.2f) GameConfig.CLIENT_FOV -= 0.1f;
                }
                if (Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
                    float[] target = GameUtils.getWorldMouse();
                    GameEntity e = new GameEntity(null, target[0], target[1], 20, 100, 10);
                    GameGlobals.entities.add(e);
                }
                // todo: fix crash
                if (Gdx.input.isKeyJustPressed(Input.Keys.I)) {
                    float[] target = GameUtils.getWorldMouse();
                    GameEntity player = GameGlobals.entities.get(GameEntity.getPlayerId());
                    GameEntity clone = new GameEntity(player.getColor(), target[0], target[1], player.getSize()*0.8f, player.getHealth(), player.getTeam());
                    for (int i = 0; i < player.getGuns().size(); i++) {
                        clone.addGun(player.getGuns().get(i));
                    }
                    clone.setControllable(true);
                    clone.setMaster(player);
                    GameGlobals.entities.add(clone);
                }
                if (Gdx.input.isKeyJustPressed(Input.Keys.T)) {
                    GameEntity player = GameGlobals.entities.get(GameEntity.getPlayerId());
                    float[] target = GameUtils.getWorldMouse();
                    player.setX(target[0]);
                    player.setY(target[1]);
                }
            }
        }
    }
}
