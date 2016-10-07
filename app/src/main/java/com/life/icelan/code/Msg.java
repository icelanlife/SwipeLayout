package com.life.icelan.code;

/**
 * Created by ccy on 2016/10/6.
 */
public class Msg {
	private String name;
	private String content;

	public Msg(String name, String content) {
		this.name = name;
		this.content = content;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "Msg{" +
				"name='" + name + '\'' +
				", content='" + content + '\'' +
				'}';
	}
}

