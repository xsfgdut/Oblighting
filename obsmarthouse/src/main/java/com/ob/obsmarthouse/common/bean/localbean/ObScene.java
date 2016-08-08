package com.ob.obsmarthouse.common.bean.localbean;

import com.ob.obsmarthouse.common.util.MathUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 情景，命名用以区分系统Scene
 * creat by ad
 */
public class ObScene implements Serializable {
	public  static final int OBSCENE_ID = 1;
	public  static final int OBSCENE_CONDITION = 2;
	public  static final int OBSCENE_ACTION = 3;



	/**
	 * 编号， 255表示该情景是当前obox中最后一个情景
	 */
	private int num;
	/**
	 * 序列号
	 */
	private int serisNum;
	/**
	 * id
	 */
	private  byte[] id;

	/**
	 * 使能状态
	 */
	private boolean isEnable;

	/**
	 * 情景条件，除了可以为正常节点外还可以是定时和遥控器
	 */
	private List<List<SceneCondition> > sceneCondition;

	/**
	 *
	 */
	private List<SceneAction> obNodes;

	public ObScene(int serisNum, byte[] id, boolean isEnable,int num) {
		this.serisNum = serisNum;
		this.id = MathUtil.validArray(id);
		this.isEnable = isEnable;
		this.num = num;
	}
	public ObScene(){

	}




	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getSerisNum() {
		return serisNum;
	}

	public void setSerisNum(int serisNum) {
		this.serisNum = serisNum;
	}

	public List<List<SceneCondition> >getSceneCondition() {
		if (sceneCondition == null) {
			sceneCondition = new ArrayList<>();
		}
		return sceneCondition;
	}

	public void setSceneCondition(List<List<SceneCondition> >sceneCondition) {
		this.sceneCondition = sceneCondition;
	}

	public List<SceneAction> getObNodes() {
		if (obNodes == null) {
			obNodes = new ArrayList<>();
		}
		return obNodes;
	}

	public void setObNodes(List<SceneAction> obNodes) {
		this.obNodes = obNodes;
	}

	public byte[] getSceneId() {
		return id;
	}
}
