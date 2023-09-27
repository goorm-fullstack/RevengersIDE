export const firstFunction = (param1: string, param2: number) => {
    return {param1: param1, param2: param2};
};

export const testFunction = () => {
    console.log(firstFunction("Hi", 1), firstFunction("Hello", 2));
};