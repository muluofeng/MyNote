
- 创建被观察者类 extends Observable
- 创建观察者类 implements Observer 实现 update(Observable o,Object arg)方法
- 被观察者添加观察者 addObserver(Observer o)
- 被观察者事件发生执行，只有在setChange()被调用后，notifyObservers()才会去调用update()
    setChanged();//设置一个内部标志位注明数据发生了变化
nitifyObservers();//这个方法会去调用观察者对象列表中所有的Observer的update()方法，通知它们数据发生了变化        
                
                
    也可以不依赖jdk 实现观察者模式       ，自己实现观察者模式     
                
                