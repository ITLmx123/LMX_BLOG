package com.limengxiang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.limengxiang.common.SystemConstant;
import com.limengxiang.domain.PageResult;
import com.limengxiang.domain.ResponseResult;
import com.limengxiang.domain.entity.Comment;
import com.limengxiang.domain.entity.User;
import com.limengxiang.domain.vo.CommentVo;
import com.limengxiang.mapper.CommentMapper;
import com.limengxiang.mapper.UserMapper;
import com.limengxiang.service.CommentService;
import com.limengxiang.utils.BeanCopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public ResponseResult commentList(String commentType,Long articleId, Integer pageNum, Integer pageSize) {
        //子评论查询
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        //查询rootId不为-1
        queryWrapper.ne(Comment::getRootId,-1);
        //根据传递过来的类型查询
        queryWrapper.eq(Comment::getType,commentType);
        List<Comment> childrenList = list(queryWrapper);
        List<CommentVo> childrenVo = BeanCopyUtil.copyBeanList(childrenList, CommentVo.class);
        queryWrapper.clear();
        //父评论查询
        //根据文章ID查询
        queryWrapper.eq(SystemConstant.ARTICLE_COMMENT_TYPE.equals(commentType),Comment::getArticleId,articleId);
        //查询rootId为-1
        queryWrapper.eq(Comment::getRootId,-1);
        //根据传递过来的类型查询
        queryWrapper.eq(Comment::getType,commentType);
        //分页查询
        Page<Comment> page = new Page<>(pageNum, pageSize);
        page(page,queryWrapper);
        List<Comment> comments = page.getRecords();
        List<CommentVo> parentVo = BeanCopyUtil.copyBeanList(comments, CommentVo.class);
        //查询所有的用户
        List<User> users = userMapper.selectList(null);
        //遍历父评论
        for (CommentVo parent: parentVo) {
            List<CommentVo> childrens = new ArrayList<>();
            for (User user: users) {
                if (user.getId().equals(parent.getCreateBy())){
                    parent.setUsername(user.getNickName());
                    parent.setAvatar(user.getAvatar());
                }
            }
            //遍历子评论
            for (CommentVo children: childrenVo) {
                //判断子评论的ToCommentUserId是否等于父评论的Id,等于则说明该子评论是该父评论的子评论
                if (children.getRootId().equals(parent.getId())){
                    for (User user: users) {
                        if (user.getId().equals(children.getCreateBy())){
                            children.setUsername(user.getNickName());
                            children.setAvatar(user.getAvatar());
                        }
                        if (user.getId().equals(children.getToCommentUserId())){
                            children.setToCommentUserName(user.getNickName());
                        }
                    }
                    //把这个子评论添加到子评论集合
                    childrens.add(children);
                }
            }
            //把子评论集合赋值给该父评论
            parent.setChildren(childrens);
        }
        PageResult pageResult = new PageResult(page.getTotal(),parentVo);
        return ResponseResult.okResult(pageResult);
    }

    @Override
    public ResponseResult addComment(Comment comment) {
        save(comment);
        return ResponseResult.okResult();
    }
}
