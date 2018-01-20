package com.lzyer.springrmi;

/**
 * @author lzyer
 * @date 2017/11/30
 */
public class AddServiceImpl implements AddService {

    @Override
    public int getAdd(int a, int b) {
        return a+b;
    }
}
