<img width="829" height="446" alt="image" src="https://github.com/user-attachments/assets/ca34b483-b059-4b2d-b521-05c05dedddfe" />


# B-Tree (B-트리)

## 📖 Project Overview
Java의 `NavigableSet<Integer>`와 호환되는 **3-way B-Tree 기반 MyThreeWayBTree**와  
`Set<Integer>`와 호환되는 **해시 기반 MyHashSet**을 직접 구현한 자료구조 프로젝트입니다.

---

## ⚙️ Tech Stack
- Java 17+
- Collection Framework
- Custom B-Tree Implementation
- Hashing / Chaining

## 🧩 Core Components
- **MyThreeWayBTree.java**: 3-way B-Tree의 삽입, 탐색, 균형 조정 로직을 구현  
- **MyThreeWayBTreeNode.java**: Node 구조 정의 (key1, key2, child1~3 등)  
- **MyHashSet.java**: B-Tree를 기반으로 한 해시셋 구현, 충돌 해결 로직 포함

## 🔍 Features
- 0~29까지의 정수를 순서대로 추가 시 올바른 트리 구조 확인
- 중복 방지 및 탐색 기능
- 직접 구현한 데이터 구조로 자바 기본 컬렉션 이해도 강화

## 💬 Comments & Insights
본 프로젝트는 자바 컬렉션 프레임워크의 내부 동작을 직접 구현해보며,  
**Tree 구조의 균형 유지, 해시 충돌 처리, 제네릭 구조 설계** 등의 핵심 개념을 학습하기 위한 과제입니다.


