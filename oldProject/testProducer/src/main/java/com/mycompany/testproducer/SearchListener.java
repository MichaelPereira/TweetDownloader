/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.testproducer;

import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.TwitterAdapter;

/**
 *
 * @author Michael
 */
public class SearchListener extends TwitterAdapter
{

  @Override
  public void gotUserTimeline(ResponseList statuses)
  {
    for (Object obj : statuses) {
      Status stat = (Status) obj;
      System.out.println(stat.getText());
    }
  }
}
