// IMyAidlInterface.aidl
package com.example.aidlserver;
import com.example.aidlserver.bean.Person;
// Declare any non-default types here with import statements
interface IMyAidlInterface {
    int addNum(int num1,int num2);
    List<Person> addPerson(in Person person);
}
