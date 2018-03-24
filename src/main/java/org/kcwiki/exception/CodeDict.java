/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kcwiki.exception;


/**
 *
 * @author iTeam_VEP
 */
public enum CodeDict {
    // Each instance provides its implementation to abstract method
    //1xx: JavaException
    //2xx: HttpError
    
    Exception_Runtime(100) {
       @Override
       public CodeDict solution() {  
          return null;
       }
    },
    Exception_UnknownHost(101) {
       @Override
       public CodeDict solution() {
          return null;
       }
    },
    HTTP_NonLastModified(201) {
       @Override
       public CodeDict solution() {
          return null;
       }
    },
    HTTP_Forbidden(202) {
       @Override
       public CodeDict solution() {
          return null;
       }
    },
    HTTP_Not_Found(203) {
       @Override
       public CodeDict solution() {
          return null;
       }
    },
    HTTP_Internal_Server_Error(204) {
       @Override
       public CodeDict solution() {
          return null;
       }
    },
    HTTP_Service_Unavailable(205) {
       @Override
       public CodeDict solution() {
          return null;
       }
    }
    ;

    public abstract CodeDict solution(); // An abstract method

    private final int codeID;     // Private variable

    CodeDict(int id) {          // Constructor
       this.codeID = id;
    }
    
    public CodeDict passCode() {
        return this;
    }

    /**
     * @return the codeID
     */
    public int getCodeID() {
        return codeID;
    }

}
