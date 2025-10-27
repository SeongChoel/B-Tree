package org.dfpl.db.hash.m20003318;

//package 이름은 org.dfpl.db.hash.m학번 입니다. 
//지키지 않을 시 반려합니다. 
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@SuppressWarnings("unused")
public class MyThreeWayBTreeNode {
	// Data Abstraction은 예시일 뿐 자유롭게 B-Tree의 범주 안에서 어느정도 수정가능
	private MyThreeWayBTreeNode parent;
	private List<Integer> keyList;
	private List<MyThreeWayBTreeNode> children;
	MyThreeWayBTreeNode()	//생성자
	{
		parent = null;
		children = new ArrayList<MyThreeWayBTreeNode>();
		keyList = new ArrayList<Integer>();
	}

	public void setKey(Integer key) { //키값넣기,정렬
		keyList.add(key);
		keyList.sort(Comparator.naturalOrder());
	}
	public List<Integer> getKeyList() {return keyList;} //키를반환
	public Integer getKeyListSize() {return keyList.size();} //키 크기 반환

	public void setParent(MyThreeWayBTreeNode parent) {this.parent = parent;} //부모 설정
	public MyThreeWayBTreeNode getParent() {return parent;} //부모 반환
	public List<MyThreeWayBTreeNode> getChildrenList(){
		return children;
	} //자식반환
	public void setChildren(MyThreeWayBTreeNode children) { //자식셋팅
		this.children.add(children);
		this.children.sort(new Comparator<MyThreeWayBTreeNode>() {
			@Override
			public int compare(MyThreeWayBTreeNode o1, MyThreeWayBTreeNode o2) {
				return o1.getKeyList().get(0).intValue() - o2.getKeyList().get(0).intValue();
			}
		});
	}


}
