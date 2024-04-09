package com.xs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xs.domain.ConsumerSongOperation;
import com.xs.domain.Song;
import com.xs.mapper.SongMapper;
import com.xs.service.ConsumerSongOperationService;
import com.xs.mapper.ConsumerSongOperationMapper;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.impl.model.GenericPreference;
import org.apache.mahout.cf.taste.impl.model.GenericUserPreferenceArray;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.UncenteredCosineSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
* @author 电脑
* @description 针对表【consumer_song_operation】的数据库操作Service实现
* @createDate 2024-03-03 13:40:09
*/
@Service
public class ConsumerSongOperationServiceImpl extends ServiceImpl<ConsumerSongOperationMapper, ConsumerSongOperation> implements ConsumerSongOperationService{

    @Resource
    private ConsumerSongOperationMapper consumerSongOperationMapper;
    @Resource
    private SongMapper songMapper;

    @Override
    public List<Song> recommendItems(String userId) throws TasteException {
        //如果用户的基础数据集太小可能推荐列表为空
        List<Long> itemIds = recommendItemIds(userId);
        List<Song> recommendSongList=new ArrayList<>();
        for (Long itemId : itemIds) {
            Song song = songMapper.selectById(itemId);
            recommendSongList.add(song);
        }
        return recommendSongList;
    }

    public List<Long> recommendItemIds(String userId) throws TasteException {
        List<ConsumerSongOperation> consumerSongOperations=consumerSongOperationMapper.getAllUserPreference();
        DataModel dataModel=createDateModel(consumerSongOperations);
        //获取用户相似程度
        UserSimilarity similarity = new UncenteredCosineSimilarity(dataModel);
        //获取用户邻居
        UserNeighborhood userNeighborhood = new NearestNUserNeighborhood(5, similarity, dataModel);
        //构建推荐器
        Recommender recommender = new GenericUserBasedRecommender(dataModel, userNeighborhood, similarity);
        //推荐2个
        List<RecommendedItem> recommendedItems = recommender.recommend(Long.parseLong(userId), 10);
        List<Long> itemIds = recommendedItems.stream().map(RecommendedItem::getItemID).collect(Collectors.toList());
        return itemIds;
    }

    private DataModel createDateModel(List<ConsumerSongOperation> consumerSongOperations) {
        System.out.println(consumerSongOperations);
        FastByIDMap<PreferenceArray> fastByIdMap = new FastByIDMap<>();
        Map<Long, List<ConsumerSongOperation>> map = consumerSongOperations.stream().collect(Collectors.groupingBy(ConsumerSongOperation::getConsumerId));
        Collection<List<ConsumerSongOperation>> list = map.values();
        for(List<ConsumerSongOperation> userPreferences : list){
            GenericPreference[] array = new GenericPreference[userPreferences.size()];
            for(int i = 0; i < userPreferences.size(); i++){
                ConsumerSongOperation userPreference = userPreferences.get(i);
                GenericPreference item = new GenericPreference(userPreference.getConsumerId(), userPreference.getSongId(), userPreference.getValue());
                array[i] = item;
            }
            fastByIdMap.put(array[0].getUserID(), new GenericUserPreferenceArray(Arrays.asList(array)));
        }
        return new GenericDataModel(fastByIdMap);
    }
}




