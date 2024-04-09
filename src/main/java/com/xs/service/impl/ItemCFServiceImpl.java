package com.xs.service.impl;

import com.xs.domain.ConsumerSongOperation;
import com.xs.domain.Song;
import com.xs.domain.SongList;
import com.xs.mapper.ConsumerSongOperationMapper;
import com.xs.service.RecommendService;
import com.xs.service.SongService;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.yarn.webapp.hamlet.Hamlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ItemCFServiceImpl implements RecommendService {

    @Resource
    private ConsumerSongOperationMapper consumerSongOperationMapper;

    // 存储用户对物品的评分信息
    //item user socre
    private Map<String, HashMap<String, Double>> userItemRatings;

    // 存储物品之间的余弦相似度
    //itemA itemB similarity
    private Map<String, HashMap<String, Double>> itemSimilarities;

    @Autowired
    private SongService songService;

//    public ItemCF(Map<String, Map<String, Double>> userItemRatings) {
//        this.userItemRatings = userItemRatings;
//        this.itemSimilarities = calculateItemSimilarities();
//    }



//    private Map<String, Map<String, Double>> calculateItemSimilarities() {
//        Map<String, Map<String, Double>> similarities = new HashMap<>();
//
//        // 获取所有物品的标识
//        Set<String> items = userItemRatings.values().stream()
//                .flatMap(ratings -> ratings.keySet().stream())
//                .collect(Collectors.toSet());
//
//        // 遍历每对物品
//        for (String itemA : items) {
//            for (String itemB : items) {
//                if (!itemA.equals(itemB)) {
//                    double similarity = calculateCosineSimilarity(itemA, itemB);
//                    similarities.computeIfAbsent(itemA, k -> new HashMap<>()).put(itemB, similarity);
//                }
//            }
//        }
//
//        return similarities;
//    }

    // 返回物品之间的余弦相似度矩阵
    // 计算物品之间的余弦相似度
    private Map<String, HashMap<String, Double>> calculateItemSimilarities() {
        Map<String, HashMap<String, Double>> similarities = new HashMap<>();

        // 遍历每对物品
        for (String itemA : userItemRatings.keySet()) {
            for (String itemB : userItemRatings.keySet()) {
                if (!itemA.equals(itemB)) {
                    double similarity = calculateCosineSimilarity(itemA, itemB);
                    similarities.computeIfAbsent(itemA, k -> new HashMap<>()).put(itemB, similarity);
                }
            }
        }

        return similarities;
    }

    // 计算余弦相似度
    private double calculateCosineSimilarity(String itemA, String itemB) {
        Set<String> V = new HashSet<>(userItemRatings.get(itemA).keySet());
        //共同喜欢的A B物品的用户
        V.retainAll(userItemRatings.get(itemB).keySet());

//        log.info("V:" + V);
        //喜欢A的用户
        Set<String> W1 = userItemRatings.get(itemA).keySet();
//        log.info("itemA:"+itemA +"   W1:"+ userItemRatings.get(itemA));
        //喜欢B的用户
        Set<String> W2 = userItemRatings.get(itemB).keySet();
//        log.info("itemB:"+itemB+"    W2:"+ userItemRatings.get(itemB));

        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;

        // 计算点积和范数
        for (String user : V) {
            double ratingA = userItemRatings.get(itemA).get(user);
            double ratingB = userItemRatings.get(itemB).get(user);

            dotProduct += ratingA * ratingB;
        }

        for (String user : W1) {
            Double ratingA = userItemRatings.get(itemA).get(user);
            normA += Math.pow(ratingA, 2);
        }
        for (String user : W2) {
//            log.info(String.valueOf(userItemRatings.get(itemB)));
            Double ratingB = userItemRatings.get(itemB).get(user);
            normB += Math.pow(ratingB, 2);
        }
        if (normA == 0 || normB == 0) {
            return 0.0; // 避免除零错误
        }

        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }

    // 基于物品相似度生成推荐结果
    public List<String> recommendItems(String user) {
        Map<String, Double> recommendations = new HashMap<>();

        // 遍历该用户未评分的物品
        for (String item : userItemRatings.keySet()) {
            if(!userItemRatings.get(item).containsKey(user)){
                //DEBUG
                log.warn(item);
                double score = calculateItemScore(user, item);
                recommendations.put(item, score);
            }
        }
        //hashMap对value升序
        ArrayList<Map.Entry<String, Double>> entries = new ArrayList<>(recommendations.entrySet());
        Collections.sort(entries, new Comparator<Map.Entry<String, Double>>() {
            @Override
            public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });
        //要返回的物品ids[]
        List<String> list = new ArrayList<>();
        for (Map.Entry<String, Double> entry : entries) {
            list.add(entry.getKey());
        }
        return list;
//        return recommendations;
    }

    // 计算物品推荐得分
    private double calculateItemScore(String user, String item) {
        double score = 0.0;

        // 遍历用户评分过的物品
        for (String ratedItem : userItemRatings.keySet()) {
            if (userItemRatings.get(ratedItem).containsKey(user)) {
                log.warn("用户评分过的物品:"+ratedItem);
                double similarity = itemSimilarities.getOrDefault(item, new HashMap<>()).getOrDefault(ratedItem, 0.0);
                double rating = userItemRatings.get(ratedItem).get(user);

                score += similarity * rating;
            }
        }
//        for (String ratedItem : userItemRatings.get(user).keySet()) {
//        }
        log.info("----改未评分过的物品由评分过的物品=>的得分为:"+score);
        return score;
    }

    @Override
    public List<Song> recommend(String userId) {
        Map<String, HashMap<String, Double>> userItemRatings=new HashMap<>();
        List<ConsumerSongOperation> allUserPreference = consumerSongOperationMapper.getAllUserPreference();
        log.warn(String.valueOf(allUserPreference));
        System.out.println(allUserPreference.toString());
        String songId,consumerId;
        double value;
        for (ConsumerSongOperation consumerSongOperation : allUserPreference) {
            songId=consumerSongOperation.getSongId().toString();
            consumerId=consumerSongOperation.getConsumerId().toString();
            value=consumerSongOperation.getValue().doubleValue();
            if (userItemRatings.containsKey(songId)) {
                HashMap<String, Double> itemHashMap = userItemRatings.get(songId);
                itemHashMap.put(consumerId, value);
            }
            else{
                HashMap<String, Double> map = new HashMap<>();
                map.put(consumerId, value);
                userItemRatings.put(songId, map);
            }
        }
        this.userItemRatings=userItemRatings;
        this.itemSimilarities=calculateItemSimilarities();
//        log.warn(String.valueOf(this.itemSimilarities));

        List<String> itemIds = recommendItems(userId);
        List<Song> songs =new ArrayList<>();
        for (String itemId : itemIds) {
            Song byId = songService.getById(itemId);
            songs.add(byId);
        }
        return songs;

//        for (String s : recommendMap.keySet()) {
//            System.out.println(s);
//        }
//        Set<String> strings = recommendMap.keySet();
//        return strings;
    }
}


//class ItemCF {
//    public static void main(String[] args) {
//        // 示例数据：用户对物品的评分信息
//        Map<String, Map<String, Double>> userItemRatings = new HashMap<>();
//        // 假设有三个用户，分别对三个物品进行了评分
//        userItemRatings.put("User1", Map.of("Item1", 5.0, "Item2", 4.0, "Item3", 3.0));
//        userItemRatings.put("User2", Map.of("Item1", 4.0, "Item2", 3.0, "Item3", 5.0));
//        userItemRatings.put("User3", Map.of("Item1", 3.0, "Item2", 5.0, "Item3", 4.0));
//
//
////        Set<String> commonUsers = new HashSet<>(userItemRatings.get("User1").keySet());
////        System.out.println(commonUsers);
//
////        // 创建ItemCF实例
////        ItemCF itemCF = new ItemCF(userItemRatings);
////
////        // 示例：为用户生成物品推荐结果
////        String targetUser = "User1";
////        Map<String, Double> recommendations = itemCF.recommendItems(targetUser);
////
////        // 打印推荐结果
////        System.out.println("Recommendations for " + targetUser + ":");
////        for (Map.Entry<String, Double> entry : recommendations.entrySet()) {
////            System.out.println(entry.getKey() + ": " + entry.getValue());
////        }
//    }
//}
