
package net;



import com.yolanda.nohttp.rest.Response;

import baseBean.ResponsePagesEntity;


/**
 * @auther AsionReachel
 * @createTime 2016/5/24 10:30
 */
public interface HttpListener<T> {

    void onSuccessful(String requestWhat, Object data, ResponsePagesEntity page);

    void onFailure(String requestWhat, Object data);


    void onFailed(int what, Response<T> response);

}
