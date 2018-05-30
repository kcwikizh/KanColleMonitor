/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kcwiki.x.httpclient.entity.kcapi.start2;

/**
 *
 * @author x5171
 */
public class Api_mst_const
{
    private Api_boko_max_ships api_boko_max_ships;

    private Api_parallel_quest_max api_parallel_quest_max;

    private Api_dpflag_quest api_dpflag_quest;

    public void setApi_boko_max_ships(Api_boko_max_ships api_boko_max_ships){
        this.api_boko_max_ships = api_boko_max_ships;
    }
    public Api_boko_max_ships getApi_boko_max_ships(){
        return this.api_boko_max_ships;
    }
    public void setApi_parallel_quest_max(Api_parallel_quest_max api_parallel_quest_max){
        this.api_parallel_quest_max = api_parallel_quest_max;
    }
    public Api_parallel_quest_max getApi_parallel_quest_max(){
        return this.api_parallel_quest_max;
    }
    public void setApi_dpflag_quest(Api_dpflag_quest api_dpflag_quest){
        this.api_dpflag_quest = api_dpflag_quest;
    }
    public Api_dpflag_quest getApi_dpflag_quest(){
        return this.api_dpflag_quest;
    }
}
