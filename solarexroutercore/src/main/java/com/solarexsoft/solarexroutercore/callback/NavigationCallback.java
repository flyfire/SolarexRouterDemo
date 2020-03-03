package com.solarexsoft.solarexroutercore.callback;

import com.solarexsoft.solarexroutercore.PostCard;

/**
 * <pre>
 *    Author: houruhou
 *    CreatAt: 21:36/2020/3/3
 *    Desc:
 * </pre>
 */

public interface NavigationCallback {
    void onFound(PostCard card);

    void onLost(PostCard card);

    void onArrival(PostCard card);
}
