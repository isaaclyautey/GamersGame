package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.physics.box2d.Box2D;

import manager.GameManager;

public class MyGdxGame extends ApplicationAdapter {
	private GameManager gameManager;
	
	@Override
	public void create () {
		gameManager = new GameManager();
		gameManager.create();
		Box2D.init();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(.25f, .25f, .25f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gameManager.update();
		gameManager.render();
	
	}
	
	@Override
	public void dispose () {
		gameManager.dispose();
	}
}
