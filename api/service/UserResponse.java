package com.offerup.postitem.api.service;

import hirondelle.date4j.DateTime;

public class UserResponse extends BaseResponse
{
    class Usertemp
    {
        DateTime dateJoined;
        String email;
        String firstName;
        int id;
        String lastName;

        public DateTime getDateJoined()
        {
            return this.dateJoined;
        }

        public String getEmail()
        {
            return this.email;
        }

        public String getFirstName()
        {
            return this.firstName;
        }

        public int getId()
        {
            return this.id;
        }

        public String getLastName()
        {
            return this.lastName;
        }
    }

    class Data
    {
        Session session;
        Usertemp user;

        public Session getSession()
        {
            return this.session;
        }
        public Usertemp getUser() { return this.user; }
    }

    public class Session
    {
        String token;
        Usertemp user;
        public String getToken()
        {
            return this.token;
        }
        public Usertemp getUser() { return this.user; }
    }

    Data data;
    User user;

    private void initUser()
    {
        this.user = new User();
        Object localObject1 = this.data.getSession();
        if (localObject1 == null) {
            return;
        }
        this.user.setToken(((UserResponse.Session)localObject1).getToken());
        localObject1 = ((UserResponse.Session)localObject1).getUser();
        this.user.setFirstName(((UserResponse.Usertemp)localObject1).getFirstName());
        this.user.setLastName(((UserResponse.Usertemp)localObject1).getLastName());
        this.user.setEmail(((UserResponse.Usertemp)localObject1).getEmail());
        this.user.setId(((UserResponse.Usertemp)localObject1).getId());
        this.user.setDateJoined(((UserResponse.Usertemp)localObject1).getDateJoined());
    }

    public User getUser()
    {
        try
        {
            if (this.user == null)
                initUser();
            User localUser = this.user;
            return localUser;
        }
        finally {}
    }

    public String getToken()
    {
        return data.getSession().getToken();
    }
}
