import React, { useEffect, useState } from 'react';

export const Clock = () => {
  const [currentTime, setCurrentTime] = useState(new Date());

  useEffect(() => {
    const timerId = setInterval(() => tick(), 1000);

    return () => {
      clearInterval(timerId);
    };
  }, []);

  const tick = () => {
    setCurrentTime(new Date());
  };

  return (
    <div>
      <div>{currentTime.toLocaleDateString()}</div>
      <div>{currentTime.toLocaleTimeString()}</div>
    </div>
  );
};
