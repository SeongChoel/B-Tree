package org.dfpl.db.hash.m20003318;
//package 이름은 org.dfpl.db.hash.m학번 입니다.
//지키지 않을 시 반려합니다. 

import java.util.*;

@SuppressWarnings("unused")
public class MyThreeWayBTree implements NavigableSet<Integer> {
    public MyThreeWayBTreeNode root;
    private LinkedList<Integer> linkedList;

    MyThreeWayBTree() { //생성자
        root = new MyThreeWayBTreeNode();
        linkedList = new LinkedList<Integer>();
    }


    public void setLinkedListByTravese(MyThreeWayBTreeNode node) {//노드순회
        if (node == null)
            return;

        if (node.getChildrenList().size() > 0) {// 왼쪽 자식 재귀적으로 순회
            setLinkedListByTravese(node.getChildrenList().get(0));
        }

        linkedList.add(node.getKeyList().get(0));

        if (node.getChildrenList().size() > 1) {// 중간 자식 재귀적으로 순회
            setLinkedListByTravese(node.getChildrenList().get(1));
        }

        if (node.getKeyList().size() > 1) { // key값이 1개이상이면 중간에 값 추가
            linkedList.add(node.getKeyList().get(1));
        }

        if (node.getChildrenList().size() > 2) { // 오른쪽 자식 재귀적으로 순회
            setLinkedListByTravese(node.getChildrenList().get(2));
        }

        if (node.getChildrenList().size() > 3) { // 오른쪽 2번째 자식 재귀적으로 순회
            setLinkedListByTravese(node.getChildrenList().get(3));
        }
    }

    @Override
    public Integer first() {
        return null;
    }

    @Override
    public Integer last() {
        return null;
    }

    public int getTotalSize(MyThreeWayBTreeNode ptN) { //재귀적으로 총 크기를 구함
        if (ptN.getChildrenList().isEmpty())
            return ptN.getKeyListSize();

        int cnt = ptN.getKeyListSize();
        for (MyThreeWayBTreeNode node : ptN.getChildrenList()) {
            cnt += getTotalSize(node);
        }
        return cnt;
    }

    @Override
    public int size() {
        return getTotalSize(root);
    }

    @Override
    public boolean isEmpty() {
        if (root.getKeyList().isEmpty())
            return true;
        return false;
    }

    @Override
    public boolean contains(Object o) {
        MyThreeWayBTreeNode ptN = root;
        if (o instanceof Number) {
            Number ins = (Number) o;
            while (true) {
                int i = 0;
                for (Integer key : ptN.getKeyList()) {
                    int comparisonResult = Integer.compare(ins.intValue(), key); // ins와 key를 비교
                    if (comparisonResult < 0) { //해당값 없음
                        break;
                    } else if (comparisonResult == 0) { //같음
                        return true;
                    } else { //ins가 key보다 크면
                        i++;
                    }
                }
                try {
                    ptN = ptN.getChildrenList().get(i); //다음 자식 노드확인
                } catch (IndexOutOfBoundsException e) {
                    return false;
                }
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public Object[] toArray() {
        return null;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    private MyThreeWayBTreeNode findNodePos(Integer e) //기준되는 노드find
    {
        MyThreeWayBTreeNode resultNode = root;
        while (true) {
            int index = 0;
            for (Integer key : resultNode.getKeyList()) {
                if (e.intValue() < key.intValue()) {
                    break;
                }
                index++;
            }
            if (resultNode.getChildrenList().isEmpty()) {
                break;
            } else {
                resultNode = resultNode.getChildrenList().get(index);
            }
        }
        return resultNode;
    }

    private void decomposition(MyThreeWayBTreeNode ptN) //노드 재정렬
    {
        while (ptN.getKeyListSize() > 2) { //max키 1보다 클때까지

            MyThreeWayBTreeNode newParent = new MyThreeWayBTreeNode();
            newParent.setKey(ptN.getKeyList().get(1));
            newParent.setParent(ptN.getParent());

            MyThreeWayBTreeNode leftChild = new MyThreeWayBTreeNode();
            leftChild.setKey(ptN.getKeyList().get(0));
            leftChild.setParent(newParent);

            MyThreeWayBTreeNode rightChild = new MyThreeWayBTreeNode();
            rightChild.setKey(ptN.getKeyList().get(2));
            rightChild.setParent(newParent);

            if (!ptN.getChildrenList().isEmpty()) {
                //왼쪽자식 셋팅
                leftChild.setChildren(ptN.getChildrenList().get(0));
                leftChild.setChildren(ptN.getChildrenList().get(1));
                ptN.getChildrenList().get(0).setParent(leftChild);
                ptN.getChildrenList().get(1).setParent(leftChild);

                //오른쪽 자식 셋팅
                rightChild.setChildren(ptN.getChildrenList().get(2));
                rightChild.setChildren(ptN.getChildrenList().get(3));
                ptN.getChildrenList().get(2).setParent(rightChild);
                ptN.getChildrenList().get(3).setParent(rightChild);
            }

            //루트 왼쪽,오른쪽자식 초기화
            newParent.setChildren(leftChild);
            newParent.setChildren(rightChild);

            //부모 노드 초기화
            MyThreeWayBTreeNode parent = ptN.getParent();
            if (parent == null) {
                root = newParent;
                break;
            } else {
                parent.getChildrenList().remove(ptN);
                parent.setKey(newParent.getKeyList().get(0));
                leftChild.setParent(parent);
                rightChild.setParent(parent);
                parent.setChildren(leftChild);
                parent.setChildren(rightChild);
            }
            ptN = parent;
        }
    }

    @Override
    public boolean add(Integer e) {
        if (contains(e))
            return false;

        MyThreeWayBTreeNode ptN = findNodePos(e);
        ptN.setKey(e);

        if (ptN.getKeyListSize() > 2) { // maxKey Property 어겼을때
            decomposition(ptN);
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends Integer> c) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        if (!contains(o)) return false;

        MyThreeWayBTreeNode ptN = findNodePos((Integer) o); //삭제할노드
        if (ptN == null) return false;

        if (ptN.getChildrenList().isEmpty()) { //리프노드이면
            ptN.getKeyList().remove(o);
        } else {
            MyThreeWayBTreeNode lessptN = findLowerNode(ptN);
            MyThreeWayBTreeNode bigptN = findHigherNode(ptN);

            ptN.getKeyList().remove(o);

            if (lessptN.getKeyListSize() > Math.floor(3 / 2)) { //왼쪽
                switchNodeKeys(ptN, lessptN, lessptN.getKeyListSize() - 1);
                ptN = lessptN;
            } else if (bigptN.getKeyListSize() > Math.floor(3 / 2)) { //오른쪽
                switchNodeKeys(ptN, bigptN, 0);
                ptN = bigptN;
            } else { //minkey일 경우
                switchNodeKeys(ptN, lessptN, lessptN.getKeyListSize() - 1);
                ptN = lessptN;
            }
        }

        refactorNodeAndParent(ptN);
        return true;
    }

    private MyThreeWayBTreeNode findLowerNode(MyThreeWayBTreeNode node) { //가장 큰값 노드 찾음
        MyThreeWayBTreeNode lessptN = node.getChildrenList().get(node.getKeyList().indexOf(node.getKeyList().get(0)));
        while (!lessptN.getChildrenList().isEmpty()) {
            lessptN = lessptN.getChildrenList().get(lessptN.getChildrenList().size() - 1);
        }
        return lessptN;
    }

    private MyThreeWayBTreeNode findHigherNode(MyThreeWayBTreeNode node) { //가장 작은값
        MyThreeWayBTreeNode bigptN = node.getChildrenList().get(node.getKeyList().indexOf(node.getKeyList().get(node.getKeyListSize() - 1)) + 1);
        while (!bigptN.getChildrenList().isEmpty()) {
            bigptN = bigptN.getChildrenList().get(bigptN.getChildrenList().size() - 1);
        }
        return bigptN;
    }

    private void switchNodeKeys(MyThreeWayBTreeNode parent, MyThreeWayBTreeNode sibling, int index) { //노드의 키를 교환함
        parent.setKey(sibling.getKeyList().get(index));
        sibling.getKeyList().remove(index);

        if (!sibling.getChildrenList().isEmpty()) {
            MyThreeWayBTreeNode children;
            if (index == sibling.getKeyListSize() - 1) {
                children = sibling.getChildrenList().get(sibling.getChildrenList().size() - 1);
                sibling.getChildrenList().remove(sibling.getChildrenList().size() - 1);
            } else {
                children = sibling.getChildrenList().get(0);
                sibling.getChildrenList().remove(0);
            }
            children.setParent(parent);
            parent.setChildren(children);
        }
    }

    private void refactorNodeAndParent(MyThreeWayBTreeNode node) {
        while (node != null) {
            MyThreeWayBTreeNode parent = node.getParent();

            if (node.getKeyListSize() < 1) {
                if (parent == null) { //루트노드인경우
                    if (!node.getChildrenList().isEmpty()) {
                        node.getChildrenList().get(0).setParent(node.getParent());
                        root = node.getChildrenList().get(0);
                    }
                    break;
                }

                MyThreeWayBTreeNode sibling = findSiblingNode(node, parent);
                int indexptN = parent.getChildrenList().indexOf(node);

                if (sibling == null) { //형제 노드 없는경우
                    mergeNodes(node, parent, indexptN);
                } else {
                    switchNodes(node, parent, sibling, indexptN);
                }
            }
            node = parent;
        }
    }

    private MyThreeWayBTreeNode findSiblingNode(MyThreeWayBTreeNode node, MyThreeWayBTreeNode parent) { //형제 찾기
        for (MyThreeWayBTreeNode sibling : parent.getChildrenList()) {
            if (isSibling(node, sibling) && (sibling.getKeyListSize() > Math.floor(3 / 2))) {
                return sibling;
            }
        }
        return null;
    }

    public boolean isSibling(MyThreeWayBTreeNode target, MyThreeWayBTreeNode node) {
        if (node.equals(target)) //target과 주어진node같으면 형제X
            return false;
        if (node.getParent().equals(target.getParent())) { //부모가 동일하면,자식에서 인덱스구함
            if (Math.abs(target.getParent().getChildrenList().indexOf(node) - target.getParent().getChildrenList().indexOf(this)) <= 1) {
                return true;
            }
            return false;
        }
        return false;
    }

    private void mergeNodes(MyThreeWayBTreeNode node, MyThreeWayBTreeNode parent, int index) { //병합
        if (index >= 1) {
            parent.getChildrenList().get(index - 1).setKey(parent.getKeyList().get(index - 1));
            parent.getKeyList().remove(index - 1);
            if (!node.getChildrenList().isEmpty()) {
                for (MyThreeWayBTreeNode child : node.getChildrenList()) {
                    child.setParent(parent.getChildrenList().get(index - 1));
                    parent.getChildrenList().get(index - 1).setChildren(child);
                }
            }
            parent.getChildrenList().remove(node);
        } else {
            parent.getChildrenList().get(index + 1).setKey(parent.getKeyList().get(index));
            parent.getKeyList().remove(index);
            if (!node.getChildrenList().isEmpty()) {
                for (MyThreeWayBTreeNode child : node.getChildrenList()) {
                    child.setParent(parent.getChildrenList().get(index + 1));
                    parent.getChildrenList().get(index + 1).setChildren(child);
                }
            }
            parent.getChildrenList().remove(node);
        }
    }

    private void switchNodes(MyThreeWayBTreeNode node, MyThreeWayBTreeNode parent, MyThreeWayBTreeNode sibling, int index) { //노드와 키를 자식 노드를 교환함, 병합,분할시
        int indexSibling = parent.getChildrenList().indexOf(sibling);

        if (index > indexSibling) {
            node.setKey(parent.getKeyList().get(indexSibling));
            parent.getKeyList().remove(indexSibling);
            parent.setKey(sibling.getKeyList().get(sibling.getKeyListSize() - 1));
            sibling.getKeyList().remove(sibling.getKeyListSize() - 1);
            if (!sibling.getChildrenList().isEmpty()) {
                MyThreeWayBTreeNode children = sibling.getChildrenList().get(sibling.getChildrenList().size() - 1);
                children.setParent(node);
                node.setChildren(children);
                sibling.getChildrenList().remove(sibling.getChildrenList().size() - 1);
            }
        } else {
            node.setKey(parent.getKeyList().get(index));
            parent.getKeyList().remove(index);
            parent.setKey(sibling.getKeyList().get(0));
            sibling.getKeyList().remove(0);
            if (!sibling.getChildrenList().isEmpty()) {
                MyThreeWayBTreeNode children = sibling.getChildrenList().get(0);
                children.setParent(node);
                node.setChildren(children);
                sibling.getChildrenList().remove(0);
            }
        }
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {
        return;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public Integer lower(Integer e) {
        return null;
    }

    @Override
    public Integer higher(Integer e) {
        return null;
    }

    @Override
    public Integer floor(Integer e) {
        return null;
    }

    @Override
    public Integer ceiling(Integer e) {
        return null;
    }

    @Override
    public Integer pollFirst() {
        return null;
    }

    @Override
    public Integer pollLast() {
        return null;
    }


    @Override
    public Iterator<Integer> iterator() {
        setLinkedListByTravese(root);
        return new MyThreeWayBTreeIterator(linkedList);
    }

    public class MyThreeWayBTreeIterator implements Iterator<Integer> { //이터레이터
        private LinkedList<Integer> linkedList;
        private int currentIndex;

        public MyThreeWayBTreeIterator(LinkedList<Integer> linkedList) { //생성자
            this.linkedList = linkedList;
            this.currentIndex = 0;
        }

        @Override
        public boolean hasNext() { //다음이있으면
            return currentIndex < linkedList.size();
        }

        @Override
        public Integer next() { //다음
            return linkedList.get(currentIndex++);
        }
    }

    @Override
    public Iterator<Integer> descendingIterator() {
        return null;
    }

    @Override
    public NavigableSet<Integer> descendingSet() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public NavigableSet<Integer> subSet(Integer fromElement, boolean fromInclusive, Integer toElement,
                                        boolean toInclusive) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public NavigableSet<Integer> headSet(Integer toElement, boolean inclusive) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public NavigableSet<Integer> tailSet(Integer fromElement, boolean inclusive) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SortedSet<Integer> subSet(Integer fromElement, Integer toElement) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SortedSet<Integer> headSet(Integer toElement) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SortedSet<Integer> tailSet(Integer fromElement) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Comparator<? super Integer> comparator() {
        // TODO Auto-generated method stub
        return null;
    }

}
