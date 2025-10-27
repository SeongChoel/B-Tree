package org.dfpl.db.hash.m20003318;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class MyHashSet implements Set<Integer> {
	//Field
	private MyThreeWayBTree[] hashTable;


	public MyHashSet(){ //생성자
		hashTable = new MyThreeWayBTree[3];
		for(int i = 0; i < hashTable.length; i++) {
			hashTable[i] = new MyThreeWayBTree();
		}
	}

	@Override
	public int size() {
		int size = 0;
		for(MyThreeWayBTree tree : hashTable) {
			size += tree.size();
		}
		return size;
	}

	@Override
	public boolean isEmpty() {
		if(size() == 0)
			return true;
		return false;
	}

	@Override
	public boolean contains(Object o) {
		if(o instanceof Integer) {
			Integer key = (Integer)o;
			return hashTable[key.intValue()%3].contains(key);
		}
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	private class MyHashIter implements Iterator<Integer> { //이터레이터
		Iterator<Integer>[] HashIt = new Iterator[hashTable.length];
		int index = 0;

		public MyHashIter() { //생성자
			for (int i = 0; i < hashTable.length; i++) {
				HashIt[i] = hashTable[i].iterator();
			}
		}

		@Override
		public boolean hasNext() {
			if (index >= HashIt.length) //해쉬보다 크면
				return false;
			if (HashIt[index].hasNext()) //다음확인
				return true;
			else {
				if (index + 1 < HashIt.length) { //(right)2번째 자식확인하기위함
					if (HashIt[++index].hasNext())
						return true;
				}
			}
			return false;
		}

		@Override
		public Integer next() {
			return HashIt[index].next();
		}

		@Override
		public void remove() {
			HashIt[index].remove();
		}

	}

	@Override
	public Iterator<Integer> iterator() {
		return new MyHashIter();
	}

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public <T> T[] toArray(T[] a) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean add(Integer e) {
		return hashTable[e%3].add(e);
	}

	@Override
	public boolean remove(Object o) {
		if(o instanceof Integer) {
			Integer key = (Integer)o;
			return hashTable[key.intValue()%3].remove(key);
		}
		return false;
	}

	@Override
	public boolean addAll(Collection<? extends Integer> c) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean removeAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

}