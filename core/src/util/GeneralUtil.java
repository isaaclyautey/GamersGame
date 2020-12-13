package util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class GeneralUtil {
	
	public static Vector3 lerpMoveCamera(float lerp, Vector3 first, Vector2 target) {
		Vector3 targetVector = new Vector3(target.x, target.y, first.z);
		first = first.lerp(targetVector, lerp);
		return first;
	}
	
	public static Vector2 lerpMove(float lerp, Vector2 first, Vector2 target) {
		return new Vector2((target.x-first.x)*lerp*Gdx.graphics.getDeltaTime(), (target.y-first.y)*lerp*Gdx.graphics.getDeltaTime());
	}
	
	

}
