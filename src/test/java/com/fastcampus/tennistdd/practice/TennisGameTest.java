package com.fastcampus.tennistdd.practice;

/**
 * Tennis Game
 * <p>
 * Rules:
 * <p>
 * 1. 플레이어는 "server", "receiver" 로 나뉨.
 * <p>
 * 2. 총 4점 이상을 획득하고, 상대방보다 2점 이상 더 많은 플레이어가 게임에서 승리.
 * <p>
 * 3. 테니스의 스코어는 0점에서 3점까지 "love", "fifteen", "thirty", "forty" 로 나타냄. ex) love all(0:0), fifteen love(1:0), forty all(3:3)...
 * <p>
 * 4. 최소 3점을 득점하고 점수가 동점인 경우, 점수는 "deuce" 이다.
 * <p>
 * 5. "deuce" 일 때 한 쪽이 점수를 득점하면 리드하는 플레이어에게 "ad(advantage)" 이다. ex) ad-in (server가 deuce 상황에서 득점), ad-out (receiver가 deuce 상황에서 득점)
 * <p>
 * 6. 점수가 6:6 이면, 7점을 먼저 획득하는 사람이 이긴다. (tie break)
 * <p>
 * 클래스에는 최소 두 개의 메서드가 있어야 한다. 스코어 조회(Read), 그리고 득점(Update). 코딩테스트가 아니므로, 클래스 디자인은 자유롭게!
 */
class TennisGameTest {
}
