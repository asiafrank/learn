package labuladong;

import java.util.*;

/**
 * 「design Twitter」是 LeetCode 上第 335 道题⽬
 * @author zhangxiaofan 2020/12/14-15:37
 */
public class DesignTwitter {
    static class Twitter {
        static int timestamp = 0;

        static class User {
            int userId;
            Set<Integer> followed; // 关注列表
            Tweet head; // 该用户发的推文

            public User(int userId) {
                this.userId = userId;
                followed = new HashSet<>();
                follow(userId);
            }

            // 关注
            public void follow(int userId) {
                followed.add(userId);
            }

            // 取关
            public void unfollow(int userId) {
                if (this.userId == userId) // 不允许取关自己
                    return;
                followed.remove(userId);
            }

            // 发文
            public void post(int tweetId) {
                Tweet t = new Tweet(tweetId, ++timestamp);
                if (head != null) {
                    t.next = head;
                }
                head = t;
            }
        }

        static class Tweet {
            int tweetId;
            int timestamp;
            Tweet next;

            public Tweet(int tweetId, int timestamp) {
                this.tweetId = tweetId;
                this.timestamp = timestamp;
            }
        }

        private Map<Integer, User> userMap = new HashMap<>();

        /**
         * user 发表⼀条 tweet 动态
         */
        public void postTweet(int userId, int tweetId) {
            User user = userMap.get(userId);
            if (user != null) {
                user.post(tweetId);
            }
        }

        /**
         * 返回该 user 关注的⼈（包括他⾃⼰）最近的动态 id，
         * 最多 10 条，⽽且这些动态必须按从新到旧的时间线顺序排列。
         */
        public List<Integer> getNewsFeed(int userId) {
            User user = userMap.get(userId);
            if (user == null)
                return null;

            // 归并排序
            PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> - (a.timestamp - b.timestamp)); // timestamp 倒序
            for (Integer uId : user.followed) {
                User u = userMap.get(uId);
                Tweet h = u.head;
                if (h != null)
                    pq.add(h);
            }

            List<Integer> feed = new ArrayList<>();
            while (!pq.isEmpty()) {
                if (feed.size() == 10) break; // 10 条就够了

                Tweet t = pq.poll();
                feed.add(t.tweetId);
                Tweet next = t.next;
                if (next != null) {
                    pq.add(next);
                }
            }
            return feed;
        }

        /** follower 关注 followee，如果 Id 不存在则新建 */
        public void follow(int followerId, int followeeId) {
            User follower = getOrCreate(followerId);
            User followee = getOrCreate(followeeId);

            follower.follow(followeeId);
        }

        /** follower 取关 followee，如果 Id 不存在则什么都不做 */
        public void unfollow(int followerId, int followeeId) {
            if (userMap.containsKey(followeeId)) {
                User follower = getOrCreate(followerId);
                follower.unfollow(followeeId);
            }
        }

        private User getOrCreate(int userId) {
            User user;
            if (userMap.containsKey(userId)) {
                user = userMap.get(userId);
            } else {
                user = new User(userId);
                userMap.put(userId, user);
            }
            return user;
        }
    }

    // twitter 使用
    public static void main(String[] args) {
        Twitter twitter = new Twitter();

        twitter.postTweet(1, 5);
        // 用户 1 发送了一条新推文 5

        twitter.getNewsFeed(1);
        // return [5]，因为自己是关注自己的

        twitter.follow(1, 2);
        // 用户 1 关注了用户 2

        twitter.postTweet(2, 6);
        // 用户2发送了一个新推文 (id = 6)

        twitter.getNewsFeed(1);
        // return [6, 5]
        // 解释：用户 1 关注了自己和用户 2，所以返回他们的最近推文
        // 而且 6 必须在 5 之前，因为 6 是最近发送的

        twitter.unfollow(1, 2);
        // 用户 1 取消关注了用户 2

        twitter.getNewsFeed(1);
        // return [5]
    }
}
